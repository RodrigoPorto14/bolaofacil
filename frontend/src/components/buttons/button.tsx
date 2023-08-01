import { ReactNode } from 'react';
import { Link } from 'react-router-dom';

type ButtonProps =
{
    children : ReactNode;
    color : string;
    hoverColor : string;
    type : "button" | "submit";
    to? : string;
    onClick? : any
}

const Button = ({children, color, hoverColor, type, to, onClick,} : ButtonProps) =>
{
    const button = 
    (
        <button type={type} 
                onClick={onClick} 
                className={`py-1 px-6 ${color} ${hoverColor} font-title text-white text-sm md:text-xl border-2 border-black rounded-xl`}
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