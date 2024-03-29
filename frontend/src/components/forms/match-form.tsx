import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import FormLayout from './form-layout';
import { useState, useEffect } from 'react';
import { makePrivateRequest } from '../../utils/request';
import SelectOptions from '../inputs/select-options';
import { Match, ResourceSample, FormProps } from '../../utils/types';
import { useParams } from 'react-router-dom';
import Input from '../inputs/input';
import InputNumber from '../inputs/input-number';
import { matchValidation, scorableMatch } from '../../utils/match-validation';
import { toGlobalDate, toLocalDate } from '../../utils/date-handler';

const MatchForm = ({ onSubmit, buttonName, resource, onDelete, create } : FormProps) =>
{

    const numberValidation = z.number()
                              .min(0,'Deve ser maior ou igual a 0')
                              .max(99, 'Deve ser menor ou igual a 99')
                              .nullable()
                              .optional()
                              
    const matchFormSchema = z.object(
    {
        ruleId : z.string()
                  .nonempty('Campo Obrigatório')
                  .transform(value => parseInt(value)),
    
        type : z.string()
                .nonempty('Campo Obrigatório'),
    
        startMoment : z.string()
                       .nonempty('Campo obrigatório')
                       .transform(value => toGlobalDate(value)),
    
        homeTeamId : z.string()
                      .nonempty('Campo Obrigatório')
                      .transform(value => parseInt(value)),
    
        awayTeamId : z.string()
                      .nonempty('Campo Obrigatório')
                      .transform(value => parseInt(value)),
        
        homeTeamScore: numberValidation,
    
        awayTeamScore: numberValidation
                                      
    }).refine(data => data.homeTeamId !== data.awayTeamId, 
                      {message: 'Os times devem ser diferentes', path: ['awayTeamId']})

      .refine(data => matchValidation(data.homeTeamScore as number, data.awayTeamScore as number, data.type), 
                      {message: 'Placar Inválido', path: ['awayTeamScore']})

      .refine(data => scorableMatch(data.homeTeamScore, data.awayTeamScore, data.startMoment),
                      {message: 'A partida ainda não começou', path: ['startMoment']})
      
        
    type MatchFormData = z.infer<typeof matchFormSchema>

    const { sweepstakeId } = useParams();
    const { register, handleSubmit, formState : { errors } } = useForm<MatchFormData>({resolver: zodResolver(matchFormSchema)});
    const [rules, setRules] = useState<ResourceSample[]>();
    const [teams, setTeams] = useState<ResourceSample[]>();
    
    const matchTypes : ResourceSample[] =
    [
        {id: 1, name: "PLACAR"},
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
            .catch((error) => {})
        
        makePrivateRequest( {url: `/boloes/${sweepstakeId}/times`} )
            .then((response) =>
            {
                setTeams(response.data)
            })
            .catch((error) => {})

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
                defaultValue={toLocalDate((resource as Match)?.startMoment)}
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
                    width='w-52'
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
                    width='w-52'
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