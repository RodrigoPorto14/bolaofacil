import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars } from '@fortawesome/free-solid-svg-icons'
import { NavLink } from 'react-router-dom'
import { useState, ReactNode } from 'react'

type NavItem =
{
    title: string;
    redirect: string;
}

type MenuLayoutProps =
{
    children: ReactNode;
    navItems: NavItem[];
    hasBackItem?: boolean;
    justify?: string;
}

const MenuLayout = ( { children, navItems, justify = 'justify-between' } : MenuLayoutProps) =>
{

    const [showMobileNav, setShowMobileNav] = useState(false);

    const changeMobileNav = () => { setShowMobileNav((prevState) => !prevState)}


    const navClass = 'flex items-center justify-center text-white font-title h-12 rounded-l-xl';

    const nav = navItems.map((navItem : NavItem) =>  
    ( 
        <NavLink to={navItem.redirect}
                 key={navItem.title} 
                 className={({ isActive }) => `${navClass} ${isActive ? "bg-brand-400" : "hover:bg-brand-500"}`}>    

            {navItem.title} 
        </NavLink> 
    )) 

    return(

        <div className="flex flex-col flex-grow items-center lg:justify-center py-12">

            <div className="flex flex-col relative pt-8 sm:p-0 sm:flex-row h-[600px] w-4/5 max-w-[1100px] bg-brand-100 rounded-xl">

                <aside className="hidden sm:flex flex-col w-48 bg-brand-200 rounded-l-xl pl-2 py-2 gap-2">

                    {nav}
            
                </aside>

                <div>
                    <FontAwesomeIcon onClick={changeMobileNav} className="sm:hidden absolute text-3xl w-8 left-2 top-2 text-zinc-200 hover:text-brand-400 hover:cursor-pointer" icon={faBars} /> 
                </div>
                

                {
                    showMobileNav && 
                    <div className="absolute z-30 flex flex-col top-10 left-1 bg-brand-200 w-32 sm:hidden rounded-xl shadow-lg gap-1">

                        {nav}

                    </div> 

                }

                <main className={`flex flex-col relative w-full h-full p-4 gap-6 ${justify}`}>

                    {children}

                </main>

            </div>

        </div>

    )
}

export default MenuLayout