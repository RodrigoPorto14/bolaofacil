import Header from '../components/header'
import Input from '../components/input';
import AuthFormLayout from '../components/auth-form-layout'
import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { makeRequest } from '../utils/request';
import { useNavigate } from 'react-router-dom';

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

    const onSubmit = (data : RegisterUserFormData) => 
    {  
        const registerData : RegisterData = {nickname: data.nickname, email: data.email, password: data.password}
        makeRequest({ url: '/users', data: registerData, method: 'POST' })
            .then((response) =>
            {
                navigate('/login')
            })
            .catch((error) => { console.log(error); })
    }

    const { register, handleSubmit, formState : { errors } } = useForm<RegisterUserFormData>({resolver: zodResolver(registerUserFormSchema)});

    return(

        <>
            <Header />

            <AuthFormLayout isLogin={false} onSubmit={handleSubmit(onSubmit)}>

                <Input<RegisterUserFormData>
                    label="Apelido"
                    type="text"
                    name="nickname"
                    register={register}
                    errors={errors}
                />

                <Input<RegisterUserFormData>
                    label="Email"
                    type="email"
                    name="email"
                    register={register}
                    errors={errors}
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