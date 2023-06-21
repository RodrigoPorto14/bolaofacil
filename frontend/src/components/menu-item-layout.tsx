import { ReactNode } from "react"
import Button from "./button"
import OverflowContainer from "./overflow-container"

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
            <OverflowContainer>

                {children}

            </OverflowContainer>

            <div className="mx-auto">
                <Button to={to}> {buttonName} </Button>
            </div>
        </>
        

    )
}

export default MenuItemLayout