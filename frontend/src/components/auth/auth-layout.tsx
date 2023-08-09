import { ReactNode } from 'react';
import { Link } from 'react-router-dom'

type AuthLayoutProps =
{
    children : ReactNode;
    isLogin? : boolean;
    customForm? : string;
}

const AuthLayout = ({ children, isLogin, customForm } : AuthLayoutProps) =>
{

    const linkClass = "flex justify-center items-center w-full font-title py-4 text-lg sm:text-xl";

    return(

        <main className="flex flex-col flex-grow items-center justify-center py-12">

            <div className="flex flex-col h-[550px] w-4/5 max-w-[550px] bg-brand-100 rounded-xl">

                <div className="flex text-white text-xl rounded-t-xl">
                    {
                        customForm ? 
                        <div className={`${linkClass} rounded-t-xl bg-brand-200`}>
                            { customForm }
                        </div>
                        :
                        <>
                            <Link to='/login' 
                            className={`${linkClass} rounded-tl-xl ${isLogin ? 'bg-brand-100' : 'bg-zinc-400 hover:bg-opacity-70'}`}> 
                            ENTRAR 
                            </Link>

                            <Link to='/register' 
                                className={`${linkClass} rounded-tr-xl ${isLogin ? 'bg-zinc-400 hover:bg-opacity-70' : 'bg-brand-100'}`}> 
                                CADASTRE-SE 
                            </Link>
                        </>
                    }
                </div>
                
                {children}
                

            </div>
            
        </main>
        

    )
}

export default AuthLayout