import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import FormLayout from './form-layout';
import Input from "../inputs/input";
import { FormProps, Sweepstake } from '../../utils/type';
import { ResourceSample } from '../../utils/type';
import SelectOptions from '../inputs/select-options';

const SweepstakeForm = ({ onSubmit, buttonName, resource, onDelete, create=false } : FormProps) =>
{
    const sweepstakeFormSchema = z.object(
    {
        name: z.string()
                .min(4, 'Deve conter no mínimo 4 caracteres')
                .max(30, 'Deve conter no máximo 30 caracteres'),

        tournament: z.string()
                     .optional(),
        
        private_: z.string()
                    .transform(value => value === "true")
    })
    
    type SweepstakeFormData = z.infer<typeof sweepstakeFormSchema>

    const { register, handleSubmit, formState : { errors } } = useForm<SweepstakeFormData>({resolver: zodResolver(sweepstakeFormSchema)});

    const tournaments: ResourceSample[] =
    [
        {id: 1, name: "PERSONALIZADO"},
        {id: 2, name: "CBLOL"}
    ]

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
                create &&
                <SelectOptions<SweepstakeFormData>
                    label="Torneio"
                    name="tournament"
                    resources={tournaments}
                    register={register}
                    errors={errors}
                    value="name"
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