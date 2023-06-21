import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import FormLayout from './form-layout';
import { useState, useEffect } from 'react';
import { makePrivateRequest } from '../utils/request';
import SelectOptions from './select-options';
import { Match, ResourceSample, FormProps } from '../utils/type';
import { useParams } from 'react-router-dom';
import Input from './input';
import InputNumber from './input-number';

const MatchForm = ({ onSubmit, buttonName, resource, onDelete, create } : FormProps) =>
{
   
    const validBestOf = (bestOf : number, scoreA : number, scoreB : number) => 
    {
        const maxScore = Math.ceil(bestOf/2);
        return (scoreA + scoreB <= bestOf) && (scoreA === maxScore || scoreB === maxScore)
    }
        
    const matchValidation = (data : any) => 
    {
        if((data.homeTeamScore === null) !== (data.awayTeamScore === null))
            return false

        for(let num = 1; num<=5; num+=2)
        {
            if(data.type === `MD${num}` && !validBestOf(num, data.homeTeamScore, data.awayTeamScore))
                return false
        }

        return true
    }

    const numberValidation = z.number()
                              .min(0,'Deve ser maior ou igual a 0')
                              .max(99,'Deve ser menor ou igual a 99')
                              .nullable()
                              .optional()
                              
    const matchFormSchema = z.object(
    {
        ruleId : z.string()
                  .transform(value => parseInt(value)),
    
        type : z.string(),
    
        startMoment : z.string()
                       .nonempty('Campo obrigatório')
                       .transform(value => value + ':00Z'),
    
        homeTeamId : z.string()
                      .transform(value => parseInt(value)),
    
        awayTeamId : z.string()
                      .transform(value => parseInt(value)),
        
        homeTeamScore: numberValidation,
    
        awayTeamScore: numberValidation
                                      
    }).refine(data => data.homeTeamId !== data.awayTeamId, {message: 'Os times devem ser diferentes', path: ['awayTeamId']})
      .refine(data => matchValidation(data), {message: 'Placar Inválido', path: ['awayTeamScore']})
      
        
    type MatchFormData = z.infer<typeof matchFormSchema>

    const { sweepstakeId } = useParams();
    const { register, handleSubmit, formState : { errors } } = useForm<MatchFormData>({resolver: zodResolver(matchFormSchema)});
    const [rules, setRules] = useState<ResourceSample[]>([]);
    const [teams, setTeams] = useState<ResourceSample[]>([]);
    
    const matchTypes : ResourceSample[] =
    [
        {id: 1, name: "SCORE"},
        {id: 2, name: "MD1"},
        {id: 3, name: "MD3"},
        {id: 4, name: "MD5"}
    ]

    useEffect(() =>
    {
        makePrivateRequest( {url: `/boloes/${sweepstakeId}/regras`} )
            .then((response) =>
            {
                setRules(response.data)
            })
            .catch((error) => console.log(error))
        
        makePrivateRequest( {url: `/boloes/${sweepstakeId}/times`} )
            .then((response) =>
            {
                setTeams(response.data)
            })
            .catch((error) => console.log(error))

    },[sweepstakeId])

    return(

        <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName={buttonName} onDelete={onDelete} create={create} resource={resource} >

            <SelectOptions<MatchFormData>
                label="Regra"
                name="ruleId"
                resources={rules}
                register={register}
                errors={errors}
                value="id"
                defaultValue={(resource as Match)?.ruleId}
            />

            <SelectOptions<MatchFormData>
                label="Tipo de Partida"
                name="type"
                resources={matchTypes}
                register={register}
                errors={errors}
                value="name"
                defaultValue={(resource as Match)?.type}
            />

            <Input<MatchFormData>
                label="Início da Partida"
                type="datetime-local"
                name="startMoment"
                register={register}
                errors={errors}
                width="w-52"
                defaultValue={(resource as Match)?.startMoment.slice(0,-4)}
            />

            <div className="flex gap-3">
                <SelectOptions<MatchFormData>
                    label="Time da Casa"
                    name="homeTeamId"
                    resources={teams}
                    register={register}
                    errors={errors}
                    value="id"
                    defaultValue={(resource as Match)?.homeTeamId}
                />

                {
                    resource &&
                    <InputNumber<MatchFormData>
                    label="Placar da Casa"
                    name="homeTeamScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Match)?.homeTeamScore}
                    />
                }  

            </div>
            
            <div className="flex gap-3">

                <SelectOptions<MatchFormData>
                    label="Time de Fora"
                    name="awayTeamId"
                    resources={teams}
                    register={register}
                    errors={errors}
                    value="id"
                    defaultValue={(resource as Match)?.awayTeamId}
                />

                {
                    resource &&
                    <InputNumber<MatchFormData>
                    label="Placar de Fora"
                    name="awayTeamScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Match)?.awayTeamScore}
                    />
                }

            </div>
            
        </FormLayout>                       

    )
}

export default MatchForm