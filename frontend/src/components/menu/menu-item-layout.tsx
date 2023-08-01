import { ReactNode } from "react"
import BackButton from "../buttons/button-back"
import MainButton from "../buttons/button-main"
import OverflowContainer from "./overflow-container"

type MenuItemLayoutProps = 
{
    children : ReactNode
    isSweepstake? : boolean;
    to : string;
    buttonName : string;
}

const MenuItemLayout = ({children, to, buttonName, isSweepstake=false} : MenuItemLayoutProps) =>
{

    return(

        <>
            <OverflowContainer>

                {children}

            </OverflowContainer>

            <div className="flex items-center mx-auto gap-4">
                { !isSweepstake && <BackButton /> }
                <MainButton to={to}> {buttonName} </MainButton>
            </div>
        </>
        

    )
}

export default MenuItemLayout