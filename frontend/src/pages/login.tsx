import Header from '../components/header'
import Input from '../components/input';
import AuthFormLayout from '../components/auth-form-layout'
import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthProvider/useAuth';

const loginUserFormSchema = z.object(
{
    username: z.string()
            .nonempty('Preencha este campo')
            .email('E-mail inválido'),
    
    password: z.string()
               .nonempty('Preencha este campo')
})

export type LoginUserFormData = z.infer<typeof loginUserFormSchema>

const Login = () =>
{
    const auth = useAuth();
    const navigate = useNavigate();
    const { register, handleSubmit, formState : { errors } } = useForm<LoginUserFormData>({resolver: zodResolver(loginUserFormSchema)});

    const onSubmit = (data : LoginUserFormData) => 
    {
        auth.authenticate(data)
            .then(() =>
            {
                navigate('/sweepstakes');
            })
    }

    return(

        <>
            <Header />

            <AuthFormLayout isLogin={true} onSubmit={handleSubmit(onSubmit)}> 

                <Input<LoginUserFormData>
                    label="Email"
                    type="email"
                    register={register}
                    name="username"
                    errors={errors}
                />

                <Input<LoginUserFormData>
                    label="Senha"
                    type="password"
                    register={register}
                    name="password"
                    errors={errors}
                />

            </AuthFormLayout>

        </>
            
    )
}

export default Login