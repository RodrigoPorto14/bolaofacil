import { ReactNode } from 'react';
import MainButton from '../buttons/button-main';
import AuthLayout from './auth-layout';

type AuthFormLayoutProps =
{
    children : ReactNode;
    isLogin : boolean;
    onSubmit : any;
}

const AuthFormLayout = ({ children, isLogin, onSubmit } : AuthFormLayoutProps) =>
{

    const buttonText = isLogin ? "ENTRAR" : "CADASTRAR";

    return(

        <AuthLayout isLogin={isLogin}> 
                
            <form onSubmit={onSubmit} className="flex flex-col h-full px-6 pt-8 gap-8">
            
                <div className="flex flex-col gap-4">

                    {children}

                </div>

                <div className="mx-auto">
                    <MainButton> {buttonText} </MainButton>
                </div>
            
            </form>
                
        </AuthLayout>
    )
}

export default AuthFormLayout