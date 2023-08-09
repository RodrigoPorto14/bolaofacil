import Header from '../components/header/header'
import Input from '../components/inputs/input';
import AuthFormLayout from '../components/auth/auth-form-layout'
import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { useEffect, useState } from 'react';
import { makeRequest } from '../utils/request';

const resetPasswordFormSchema = z.object(
{
    password: z.string()
               .nonempty('Campo obrigatório')
               .min(6,'Deve conter no mínimo 6 caracteres'),
        
    confirm: z.string()
              .nonempty('Campo obrigatório')
              .min(6,'Deve conter no mínimo 6 caracteres')

}).refine((data) => data.password === data.confirm, {message: 'As senhas não correspondem', path: ['confirm']})

export type ResetPasswordFormData = z.infer<typeof resetPasswordFormSchema>

const ResetPassword = () =>
{
    const { token } = useParams();
    const navigate = useNavigate();
    const { register, handleSubmit, formState : { errors } } = useForm<ResetPasswordFormData>({resolver: zodResolver(resetPasswordFormSchema)});
    const [validToken, setValidToken] = useState(false);

    useEffect(() =>
    {
        const params = {token}
        makeRequest({ url: '/users/validate-token', params, method : 'POST'})
            .then(() =>
            {
                setValidToken(true);
            })
            .catch(() => { setValidToken(false); })
    },[])

    const onSubmit = (data : ResetPasswordFormData) => 
    {
        const params = {token, newPassword : data.password}
        makeRequest({url : '/users/password-reset', params, method : 'PUT'})
            .then(response =>
            {
                toast.success(response.data);
                navigate('/login')
            })
            .catch(error => { toast.error(error.response.data.message) })
    }

    return(

        <>
            <Header />

            <AuthFormLayout customForm="RECUPERAÇÃO DE SENHA" onSubmit={handleSubmit(onSubmit)} buttonText="MUDAR SENHA"> 

                {
                    validToken &&
                    <>
                        <Input<ResetPasswordFormData>
                            label="Nova Senha"
                            type="password"
                            name="password"
                            register={register}
                            errors={errors}
                        />

                        <Input<ResetPasswordFormData>
                            label="Confirmar Nova Senha"
                            type="password"
                            name="confirm"
                            register={register}
                            errors={errors}
                        />
                    </>
                }

            </AuthFormLayout>

        </>
            
    )
}

export default ResetPassword