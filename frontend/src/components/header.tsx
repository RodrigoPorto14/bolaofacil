import { Link } from "react-router-dom";
import Button from "./button"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthProvider/useAuth";

const logo = require('../img/logo.png') as string;

type HeaderProps = 
{
    status?: string;
};

const Header = ( { status } : HeaderProps)  =>
{
    const navigate = useNavigate();
    const auth = useAuth();

    const handleLogout = () =>
    {
        auth.logout();
        navigate('/');
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
                    <p className="text-white">{auth.nickname}</p>
                    <FontAwesomeIcon onClick={handleLogout} className="text-white cursor-pointer hover:text-brand-400" icon={faRightFromBracket} />
                </div>
                
            }
            
        </header>

    )
}

export default Header