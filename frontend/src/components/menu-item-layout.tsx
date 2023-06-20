import { ReactNode } from "react"
import Button from "./button"

type MenuItemLayoutProps = 
{
    children : ReactNode
    to : string;
    buttonName : string;
}

const MenuItemLayout = ({children, to, buttonName} : MenuItemLayoutProps) =>
{
    return(

        <>
            <div className="flex flex-col h-4/5 gap-2 overflow-y-auto ">

                {children}

            </div>

            <div className="mx-auto">
                <Button to={to}> {buttonName} </Button>
            </div>
        </>
        

    )
}

export default MenuItemLayout