import Header from "../components/header";
import Ranking from "../components/ranking";
import Bets from "../components/bets";
import { Link, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faGear } from '@fortawesome/free-solid-svg-icons'

const Sweepstake = () =>
{
    const location = useLocation();
    return(

        <>
            <Header status="logged" />

            <div className="flex flex-col">

                <div className="flex items-center justify-center p-2">

                    <h1 className="font-title text-2xl p-4">NOME DO BOLÃO</h1>

                    <Link to={`${location.pathname}/config/sweepstake`}>
                        <FontAwesomeIcon 
                            className="text-black text-4xl hover:text-brand-200 hover:cursor-pointer" 
                            icon={faGear}
                        />
                    </Link>
                    
                </div>
                

                <div className="flex flex-col gap-8 px-8 xl:flex-row justify-center">

                    <Ranking />

                    <Bets />

                </div>
            </div>
        </>
        
    )

}

export default Sweepstake;