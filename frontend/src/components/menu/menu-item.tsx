import { ReactNode } from "react"
import { Link } from "react-router-dom"

type MenuItemProps =
{
    children : ReactNode;
    redirect? : string;
}

const MenuItem = ( {children, redirect} : MenuItemProps) =>
{
    return(
        <>
         {
            redirect ? 
            <Link 
                to={redirect} 
                className="flex bg-white hover:bg-brand-200 hover:text-white rounded-xl text-black justify-between items-center py-2 px-4">

                {children}
            </Link> 
            :
            <div className="flex bg-white rounded-xl text-black justify-between items-center py-2 px-4">
                {children}
            </div>
         }   
        </>

    )
}

export default MenuItem;