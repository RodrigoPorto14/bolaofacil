import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { useNavigate } from "react-router-dom";
import { zodResolver } from '@hookform/resolvers/zod'
import { useAuth } from '../../context/AuthProvider/useAuth';
import { makePrivateRequest } from '../../utils/request';
import { toast } from 'react-toastify';
import FormLayout from './form-layout';
import Input from '../inputs/input';

const UserForm = ({ setIsUserForm } : { setIsUserForm : (b : boolean) => void }) =>
{
    const userFormSchema = z.object(
    {
        nickname: z.string()
                    .min(4, 'Deve conter no mínimo 4 caracteres')
                    .max(16, 'Deve conter no máximo 16 caracteres')
                    .refine(value => /^[a-zA-Z0-9]*$/.test(value), {message: 'Não pode conter espaços ou caracteres especiais'})
    
    })
    
    type UserFormData = z.infer<typeof userFormSchema>;
    const { register, handleSubmit, formState : { errors } } = useForm<UserFormData>({resolver: zodResolver(userFormSchema)});;
    const user = useAuth();
    const navigate = useNavigate();

    const onSubmit = (data : UserFormData) => 
    {  
        if(data.nickname !== user.nickname)
            makePrivateRequest( {url : '/users' , data , method: 'PUT'} )
                .then((response) =>
                {
                    navigate(0);
                    
                })
                .catch(error => 
                {
                    const errorMessage : string = error.response.data.message;
                    if(errorMessage.includes("nickname"))
                        toast.error("Apelido já existente");
                })
    }

    return(

        <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName="ATUALIZAR" create={true} backButton={false}>
                             
            <Input<UserFormData>
                label="Apelido"
                type="text"
                name="nickname"
                register={register}
                errors={errors}
                defaultValue={user.nickname}
                //customError={existingNickname ? "Apelido já existente" : ''}
            />
            
            <div className="flex w-full justify-end">
                <button className="hover:text-white cursor-pointer" onClick={() => setIsUserForm(false)}> Alterar senha? </button>
            </div>

        </FormLayout>
    )
}

export default UserForm;

