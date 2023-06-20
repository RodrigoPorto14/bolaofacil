import { Link } from "react-router-dom";
import Button from "./button"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons'
import { getAuthenticatedNickName } from "../utils/auth";
import { logout } from "../utils/auth";
import { useNavigate } from "react-router-dom";

const logo = require('../img/logo.png') as string;

type HeaderProps = 
{
    status?: string;
};

const Header = ( { status } : HeaderProps)  =>
{
    const navigate = useNavigate();

    const handleLogout = () =>
    {
        logout();
        navigate(0)
    }

    return(

        <header className="flex justify-around items-center bg-brand-200 h-24 ">

            <Link to='/'> 
                <img className="h-24 w-24" src={logo} alt="Logo Bolão Fácil" /> 
            </Link>
            
            { status === 'not-logged' &&  <Button to="/login" > ENTRAR </Button>  }

            { 
                status === 'logged' && 
                <div className="flex items-center gap-2 text-xl">
                    <p className="text-white">{getAuthenticatedNickName()}</p>
                    <FontAwesomeIcon onClick={handleLogout} className="text-white cursor-pointer hover:text-brand-400" icon={faRightFromBracket} />
                </div>
                
            }
            
        </header>

    )
}

export default Header