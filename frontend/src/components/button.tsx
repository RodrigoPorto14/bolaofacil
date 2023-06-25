import { ReactNode } from 'react';
import { Link } from 'react-router-dom';

type ButtonProps =
{
    children : ReactNode;
    to? : string;
    onClick? : any
    isDelete? : boolean;
}

const Button = ({children, to, onClick, isDelete=false} : ButtonProps) =>
{
    const buttonType = isDelete ? 'button' : 'submit'
    const buttonColor = isDelete ? 'bg-red-600' : 'bg-brand-300'
    const buttonHoverColor = isDelete ? 'hover:bg-red-800' : 'hover:bg-brand-400'

    const button = 
    (
        <button type={buttonType} 
                onClick={onClick} 
                className={`py-1 px-8 ${buttonColor} ${buttonHoverColor} font-title text-white text-xl border-2 border-black rounded-xl`}
        >
            {children}
        </button>
    )

    return(

        <>
        
            {to ? <Link to={to}>{button}</Link> : button}
        
        </>

    )
}

export default Button