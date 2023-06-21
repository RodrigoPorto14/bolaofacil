import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import FormLayout from './form-layout';
import Input from "./input";
import { FormProps, Sweepstake } from '../utils/type';

const SweepstakeForm = ({ onSubmit, buttonName, resource, onDelete, create=false } : FormProps) =>
{
    const sweepstakeFormSchema = z.object(
    {
        name: z.string()
                .min(4, 'Deve conter no mínimo 4 caracteres')
                .max(30, 'Deve conter no máximo 30 caracteres'),
        
        private_: z.string()
                    .transform(value => value === "true")
    })
    
    type SweepstakeFormData = z.infer<typeof sweepstakeFormSchema>

    const { register, handleSubmit, formState : { errors } } = useForm<SweepstakeFormData>({resolver: zodResolver(sweepstakeFormSchema)});

    return(

        <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName={buttonName} onDelete={onDelete} create={create} resource={resource} gap="gap-8" >

            <Input<SweepstakeFormData>
                label="Nome do Bolão"
                type="text"
                name="name"
                register={register}
                errors={errors}
                defaultValue={(resource as Sweepstake)?.name}
            />
            
        
            <div className="flex flex-row-reverse gap-4">

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