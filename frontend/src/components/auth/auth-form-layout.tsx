import { ReactNode } from 'react';
import MainButton from '../buttons/button-main';
import AuthLayout from './auth-layout';
import { Link } from 'react-router-dom';

type AuthFormLayoutProps =
{
    children : ReactNode;
    isLogin? : boolean;
    onSubmit : any;
    customForm? : string;
    buttonText? : string;
}

const AuthFormLayout = ({ children, isLogin, onSubmit, customForm, buttonText } : AuthFormLayoutProps) =>
{

    return(

        <AuthLayout isLogin={isLogin} customForm={customForm}> 
                
            <form onSubmit={onSubmit} className="flex flex-col h-full px-6 pt-8 gap-8">
            
                <div className="flex flex-col gap-4">

                    {children}

                </div>

                {
                    buttonText &&
                    <div className="flex flex-col items-center gap-4">
                        <MainButton> {buttonText} </MainButton>
                        {
                            isLogin && 
                            <Link to="/password" className="hover:text-white">
                                Esqueceu a senha?
                            </Link>
                        }
                    </div>
                }
                
            
            </form>
                
        </AuthLayout>
    )
}

export default AuthFormLayout