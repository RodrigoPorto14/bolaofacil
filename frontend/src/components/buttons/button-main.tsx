import { ReactNode } from "react";
import Button from "./button"

type MainButtonProps = 
{
    children : ReactNode;
    onClick? : any;
    to? : string;
}

const MainButton = ({ children, onClick, to } : MainButtonProps) =>
{
    return(

        <Button color="bg-brand-300" hoverColor="hover:bg-brand-400" type="submit" to={to} onClick={onClick}> {children} </Button>
    )
}

export default MainButton;