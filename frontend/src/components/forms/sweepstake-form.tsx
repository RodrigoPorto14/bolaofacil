import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import FormLayout from './form-layout';
import Input from "../inputs/input";
import { FormProps, Sweepstake } from '../../utils/types';
import { ResourceSample } from '../../utils/types';
import SelectOptions from '../inputs/select-options';
import { useEffect, useState } from 'react';
import { makePrivateRequest } from '../../utils/request';

const SweepstakeForm = ({ onSubmit, buttonName, resource, onDelete, create=false } : FormProps) =>
{
    const sweepstakeFormSchema = z.object(
    {
        name: z.string()
                .min(4, 'Deve conter no mínimo 4 caracteres')
                .max(25, 'Deve conter no máximo 25 caracteres'),

        leagueId: z.string().transform(value => Number(value)),
        
        private_: z.string().transform(value => value === "true")
    })
    
    type SweepstakeFormData = z.infer<typeof sweepstakeFormSchema>

    const { register, handleSubmit, formState : { errors } } = useForm<SweepstakeFormData>({resolver: zodResolver(sweepstakeFormSchema)});

    const [leagues, setLeagues] = useState<ResourceSample[]>();

    useEffect(() =>
    {
        makePrivateRequest( { url: '/leagues' } )
            .then((response) =>
            {
                setLeagues(response.data)
            })
            .catch((error) => console.log(error))

    },[])

    return(

        <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName={buttonName} onDelete={onDelete} create={create} resource={resource} >

            <Input<SweepstakeFormData>
                label="Nome do Bolão"
                type="text"
                name="name"
                register={register}
                errors={errors}
                defaultValue={(resource as Sweepstake)?.name}
            />

            { 
                (create && leagues) &&
                <SelectOptions<SweepstakeFormData>
                    label="Torneio"
                    name="leagueId"
                    resources={leagues}
                    register={register}
                    errors={errors}
                    value="id"
                    defaultValue={(resource as Sweepstake)?.tournament}
                /> 
            }
            
        
            <div className="flex flex-row-reverse mt-4 gap-4">

                <label className="flex gap-1 items-center">
                    <input
                        className="h-6 w-6"
                        value="true"
                        type="radio"
                        {...register("private_")}
                        name="private_"
                        defaultChecked={(resource as Sweepstake)?.private_}
                    />
                    Privado
                </label>

                <label className="flex gap-1 items-center" >
                    <input
                        className="h-6 w-6"
                        value="false"
                        type="radio"
                        {...register("private_")}
                        name="private_"
                        defaultChecked={!(resource as Sweepstake)?.private_}
                    />
                    Público
                </label>

            </div>
            
        </FormLayout>
    )
}

export default SweepstakeForm