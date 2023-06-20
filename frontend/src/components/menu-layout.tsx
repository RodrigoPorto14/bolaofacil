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
    justify?: string;
}

const MenuLayout = ( { children, navItems, justify } : MenuLayoutProps) =>
{

    const [showMobileNav, setShowMobileNav] = useState(false);

    const changeMobileNav = () => { setShowMobileNav((prevState) => !prevState)}

    const nav = (classes : string) => navItems.map((navItem : NavItem) =>  
    ( 
        <NavLink to={navItem.redirect}
                 key={navItem.title} 
                 className={({ isActive }) => `${classes} ${isActive ? "bg-brand-400" : "hover:bg-brand-500"}`}>    

            {navItem.title} 
        </NavLink> 
    )) 

    return(

        <div className="flex flex-col items-center py-10">

            <div className="flex flex-col relative lg:flex-row h-[550px] w-[400px] sm:w-2/3 bg-brand-100 rounded-xl">

                <aside className="hidden lg:flex flex-col w-48 bg-brand-200 rounded-l-xl pl-2 py-2 gap-2">

                    {nav('flex items-center justify-center text-white font-title h-12 rounded-l-xl')}
            
                </aside>

                <div>
                    <FontAwesomeIcon onClick={changeMobileNav} className="lg:hidden text-3xl w-8 p-1 text-zinc-200 hover:text-brand-400 hover:cursor-pointer" icon={faBars} /> 
                </div>
                

                {
                    showMobileNav && 
                    <div className="absolute z-50 flex flex-col top-10 left-1 bg-brand-200 w-32 lg:hidden rounded-xl shadow-lg gap-1">

                        {nav('flex items-center justify-center text-white font-title h-12 rounded-xl')}

                    </div> 

                }

                <main className={`flex flex-col w-full h-[520px] p-4 gap-6 ${justify ?? ''}`}>

                    {children}

                </main>

            </div>

        </div>

    )
}

export default MenuLayout