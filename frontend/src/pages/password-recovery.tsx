import Header from '../components/header/header'
import Input from '../components/inputs/input';
import AuthFormLayout from '../components/auth/auth-form-layout'
import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { toast } from 'react-toastify';
import { makeRequest } from '../utils/request';
import { FRONTEND_URL } from '../utils/request';

const recoveryPasswordFormSchema = z.object(
{
    email: z.string()
                .nonempty('Campo obrigatório')
                .email('E-mail inválido')

})

export type RecoveryPasswordFormData = z.infer<typeof recoveryPasswordFormSchema>

const RecoveryPassword = () =>
{
    const { register, handleSubmit, formState : { errors } } = useForm<RecoveryPasswordFormData>({resolver: zodResolver(recoveryPasswordFormSchema)});

    const onSubmit = (data : RecoveryPasswordFormData) => 
    {
        const params = {email : data.email}
        makeRequest({url : '/users/password-recovery', params, method : 'POST'})
            .then(response =>
            {
                toast.success(
                    "Recuperação de senha solicitada com sucesso! Um link será enviado ao seu email para a mudança de senha", 
                    {autoClose : false});

                const params = {url: FRONTEND_URL, token: response.data, type : "PASSWORD_RECOVERY"};

                makeRequest({ url: '/users/send-email', params, method: 'POST'})

            })
            .catch(error => { toast.error(error.response.data.message) })
    }

    return(

        <>
            <Header />

            <AuthFormLayout customForm="RECUPERAÇÃO DE SENHA" onSubmit={handleSubmit(onSubmit)} buttonText="ENVIAR"> 

                <Input<RecoveryPasswordFormData>
                        label="Email"
                        type="email"
                        name="email"
                        register={register}
                        errors={errors}
                    />

            </AuthFormLayout>

        </>
            
    )
}

export default RecoveryPassword