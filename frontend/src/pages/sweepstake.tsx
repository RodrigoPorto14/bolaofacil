import Header from "../components/header/header";
import Ranking from "../components/sweepstake/ranking";
import Bets from "../components/sweepstake/bets";
import { Link, useLocation, useParams } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faGear } from '@fortawesome/free-solid-svg-icons'
import { useParticipant } from "../context/ParticipantProvider/useParticipant";
import { useEffect, useState } from 'react'
import { RankingHeader, Bet } from '../utils/type'
import { makePrivateRequest } from "../utils/request";
import { toast } from "react-toastify";

const Sweepstake = () =>
{
    const location = useLocation();
    const participant = useParticipant();
    const isOwner = participant.role === "OWNER";
    const isOwnerOrAdmin = isOwner || participant.role === "ADMIN";

    const { sweepstakeId } = useParams();
    const [bets, setBets] = useState<Bet[]>([]);
    const [pageNumber, setPageNumber] = useState(0);
    const [lastPage, setLastPage] = useState(true);
    const [ranking, setRanking] = useState<RankingHeader[]>([]);

    useEffect(() =>
    {
        getMatcheswithBets();

    },[])

    const getMatcheswithBets = (page? : number) =>
    {
        const params = page !== undefined ? { page } : {};

        makePrivateRequest({ url: `/boloes/${sweepstakeId}/details`, params})
            .then(response =>
            {
                const bets = response.data.bets;
                const ranking = response.data.ranking;

                const fetchedBets = bets.content.map((bet: Bet) => 
                ({
                    ...bet,
                    originalHomeTeamScore: bet.homeTeamScore,
                    originalAwayTeamScore: bet.awayTeamScore,
                    error: false
                }));
                
                setPageNumber(bets.pageable.pageNumber);
                setLastPage(bets.last);
                setBets(fetchedBets);
                setRanking(ranking)
            })
            .catch(error => { toast.error(error.response.data.error_description); })
    }
    
    return(

        <>
            <Header status="logged" />

            <div className="flex flex-col">

                <div className="flex items-center justify-center p-2">

                    <h1 className="font-title text-2xl p-4"> {participant.sweepstakeName} </h1>

                    {
                        isOwnerOrAdmin &&
                        <Link to={`${location.pathname}/${isOwner ? 'info' : 'rules'}`}>
                            <FontAwesomeIcon 
                                className="text-black text-4xl hover:text-brand-200 hover:cursor-pointer" 
                                icon={faGear}
                            />
                        </Link>
                    }
                    
                </div>
                

                <div className="flex flex-col gap-8 px-8 xl:flex-row justify-center">

                    <Ranking ranking={ranking} />
                    <Bets bets={bets} pageNumber={pageNumber} lastPage={lastPage} setBets={setBets} getMatcheswithBets={getMatcheswithBets}/>

                </div>
            </div>
        </>
        
    )

}

export default Sweepstake;