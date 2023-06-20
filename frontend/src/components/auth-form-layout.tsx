import { ReactNode } from 'react';
import Button from './button';
import { Link } from 'react-router-dom'


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

        <main className="flex flex-col flex-1 items-center py-12">

            <div className="flex flex-col h-[550px] w-[400px] md:w-[550px] bg-brand-100 rounded-xl">

                <div className="flex h-16 w-full text-white text-xl rounded-t-xl">

                    <Link to='/login' 
                          className={`flex justify-center items-center w-full font-title rounded-tl-xl  ${isLogin ? 'bg-brand-100' : 'bg-zinc-400 hover:bg-opacity-70'}`}> 
                          ENTRAR 
                    </Link>

                    <Link to='/register' 
                          className={` flex justify-center items-center w-full font-title rounded-tr-xl ${isLogin ? 'bg-zinc-400 hover:bg-opacity-70' : 'bg-brand-100'}`}> 
                          CADASTRE-SE 
                    </Link>

                </div>
                
                <form onSubmit={onSubmit} className="flex flex-col h-full px-6 py-12 justify-between">
                
                    <div className="flex flex-col gap-4">

                        {children}

                    </div>

                    <div className="mx-auto">
                        <Button> {buttonText} </Button>
                    </div>
                
                </form>
                

            </div>
            
        </main>
        

    )
}

export default AuthFormLayout