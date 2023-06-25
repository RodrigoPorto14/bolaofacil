import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import FormLayout from './form-layout';
import Input from "./input";
import { FormProps, Team } from '../utils/type';

const TeamForm = ({ onSubmit, buttonName, resource, onDelete, create=false } : FormProps) =>
{
    const teamFormSchema = z.object(
    {
        name: z.string()
                .nonempty('Campo Obrigatório')
                .max(16, 'Deve conter no máximo 16 caracteres'),
        
        imgUri: z.instanceof(FileList)
                    .transform(list => list.item(0))
    })
    
    type TeamFormData = z.infer<typeof teamFormSchema>

    const { register, handleSubmit, formState : { errors } } = useForm<TeamFormData>({resolver: zodResolver(teamFormSchema)});

    return(

        <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName={buttonName} onDelete={onDelete} create={create} resource={resource} >

            <Input<TeamFormData>
                label="Nome do Time"
                type="text"
                name="name"
                register={register}
                errors={errors}
                defaultValue={(resource as Team)?.name}
            />

            <div className={`flex flex-col`}>

                <label className="px-1">Escudo do Time</label>

                <input
                type="file"
                accept="image/*"
                {...register("imgUri")}
                />

                {errors.imgUri && <span className="px-1 text-sm text-red-600">{errors.imgUri.message}</span>}

            </div>
            
        </FormLayout>
    )
}

export default TeamForm