import Header from '../../components/header/header'
import Input from '../../components/inputs/input';
import AuthFormLayout from '../../components/auth/auth-form-layout'
import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { makeRequest } from '../../utils/request';
import { useNavigate } from 'react-router-dom';
import { FRONTEND_URL } from '../../utils/request';
import { useState } from 'react';
import { toast } from 'react-toastify';

const registerUserFormSchema = z.object(
    {
        nickname: z.string()
                   .min(4, 'Deve conter no mínimo 4 caracteres')
                   .max(16, 'Deve conter no máximo 16 caracteres')
                   .refine(value => /^[a-zA-Z0-9]*$/.test(value), {message: 'Não pode conter espaços ou caracteres especiais'}),
               
        email: z.string()
                .nonempty('Campo obrigatório')
                .email('E-mail inválido'),
        
        password: z.string()
                   .nonempty('Campo obrigatório')
                   .min(6,'Deve conter no mínimo 6 caracteres'),
        
        confirm: z.string()
                  .nonempty('Campo obrigatório')
                  .min(6,'Deve conter no mínimo 6 caracteres')

    }).refine((data) => data.password === data.confirm, {message: 'As senhas não correspondem', path: ['confirm']})
    
type RegisterUserFormData = z.infer<typeof registerUserFormSchema>

export type RegisterData = 
{
  nickname: String;
  email: String;
  password: String;
}

const Register = () =>
{
    const navigate = useNavigate();
    const [existingEmail, setExistingEmail] = useState(false);
    const [existingNickname, setExistingNickname] = useState(false);

    const onSubmit = (data : RegisterUserFormData) => 
    {  
        const registerData : RegisterData = {nickname: data.nickname, email: data.email, password: data.password}
        makeRequest({ url: '/users', data: registerData, method: 'POST' })
            .then((response) =>
            {
                toast.success("Cadastro feito com sucesso! Um link será enviado ao seu email para ativação da conta", {autoClose : false});
                setExistingEmail(false);
                setExistingNickname(false);

                const params = {url: FRONTEND_URL, token: response.data, type : "VERIFICATION"};

                makeRequest({ url: '/users/send-email', params, method: 'POST'})

                navigate('/login')
            })
            .catch((error) => 
            { 
                const errorMessage : string = error.response.data.message;
                setExistingEmail(errorMessage.includes("email"));
                setExistingNickname(errorMessage.includes("nickname"))
            })
    }

    const { register, handleSubmit, formState : { errors } } = useForm<RegisterUserFormData>({resolver: zodResolver(registerUserFormSchema)});

    return(

        <>
            <Header />

            <AuthFormLayout onSubmit={handleSubmit(onSubmit)} buttonText="CADASTRAR">

                <Input<RegisterUserFormData>
                    label="Apelido"
                    type="text"
                    name="nickname"
                    register={register}
                    errors={errors}
                    customError={existingNickname ? "Apelido já existente" : ''}
                />

                <Input<RegisterUserFormData>
                    label="Email"
                    type="email"
                    name="email"
                    register={register}
                    errors={errors}
                    customError={existingEmail ? "Email já cadastrado" : ''}
                />

                <Input<RegisterUserFormData>
                    label="Senha"
                    type="password"
                    name="password"
                    register={register}
                    errors={errors}
                />

                <Input<RegisterUserFormData>
                    label="Confirmar Senha"
                    type="password"
                    name="confirm"
                    register={register}
                    errors={errors}
                />
            
            </AuthFormLayout>
        </>
            
    )
}

export default Register