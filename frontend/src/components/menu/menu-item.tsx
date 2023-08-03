import { ReactNode } from "react"
import { Link } from "react-router-dom"

type MenuItemProps =
{
    children : ReactNode;
    redirect? : string;
}

const MenuItem = ( {children, redirect} : MenuItemProps) =>
{
    const menuItemClass = "flex bg-white rounded-xl text-sm sm:text-base text-black justify-between items-center py-2 px-4";
    return(
        <>
         {
            redirect ? 
            <Link 
                to={redirect} 
                className={`${menuItemClass} hover:bg-brand-200 hover:text-white rounded-xl`}>

                {children}
            </Link> 
            :
            <div className={menuItemClass}>
                {children}
            </div>
         }   
        </>

    )
}

export default MenuItem;