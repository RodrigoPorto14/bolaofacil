import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { makePrivateRequest } from '../../utils/request';
import { toast } from 'react-toastify';
import FormLayout from './form-layout';
import Input from '../inputs/input';
import { useNavigate } from 'react-router-dom';

const PasswordForm = ({ setIsUserForm } : { setIsUserForm : (b : boolean) => void }) =>
{
    const passwordValidation = z.string().nonempty('Campo obrigatório').min(6,'Deve conter no mínimo 6 caracteres');

    const passwordFormSchema = z.object(
    {
        oldPassword: passwordValidation,
        newPassword: passwordValidation,
        confirmNewPassword: passwordValidation
    })
    
    type PasswordFormData = z.infer<typeof passwordFormSchema>
    const { register, handleSubmit, formState : { errors } } = useForm<PasswordFormData>({resolver: zodResolver(passwordFormSchema)});
    const navigate = useNavigate();

    const onSubmit = (data : PasswordFormData) => 
    {  
        makePrivateRequest( {url : '/users/password' , data, method: 'PUT'} )
            .then((response) =>
            {
                toast.success("Senha alterada com sucesso!");
                setIsUserForm(true);
            })
            .catch(error => { toast.error(error.response.data.message); })
    }

    return(

        <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName="ATUALIZAR" create={true}>
                             
            <Input<PasswordFormData>
                label="Senha antiga"
                type="password"
                name="oldPassword"
                register={register}
                errors={errors}
            />

            <Input<PasswordFormData>
                label="Nova senha"
                type="password"
                name="newPassword"
                register={register}
                errors={errors}
            />

            <Input<PasswordFormData>
                label="Confirmar nova senha"
                type="password"
                name="confirmNewPassword"
                register={register}
                errors={errors}
            />

            <div className="flex w-full justify-end">
                <p className="hover:text-white cursor-pointer" onClick={() => setIsUserForm(true)}> Alterar usuário? </p>
            </div>

        </FormLayout>
    )
}

export default PasswordForm;