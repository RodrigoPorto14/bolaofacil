import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCaretLeft, faCaretRight } from '@fortawesome/free-solid-svg-icons'
import { useEffect, useState } from 'react'
import { Bet } from '../utils/type'
import { makePrivateRequest } from '../utils/request'
import { useParams } from 'react-router-dom'
import { ChangeEvent } from 'react'
import Button from './button'
import { isPastDate, toBrDate } from '../utils/date-handler'
import { notChangeBet, invalidBet } from '../utils/match-validation'

type BetsProps =
{
    bets : Bet[];
    pageNumber : number;
    lastPage : boolean;
    setBets : (bets : Bet[]) => void
    getMatcheswithBets : (page? : number) => void
}

const Bets = ({bets, pageNumber, lastPage, setBets, getMatcheswithBets} : BetsProps) =>
{

    const {sweepstakeId} = useParams();

    const sendBets = (bet : Bet, method : string) =>
    {
        bet.originalHomeTeamScore = bet.homeTeamScore;
        bet.originalAwayTeamScore = bet.awayTeamScore;
        bet.error = false;
        const data = {matchId: bet.match.id, homeTeamScore: bet.homeTeamScore, awayTeamScore: bet.awayTeamScore};
        makePrivateRequest({url : `boloes/${sweepstakeId}/bets`, data, method})
            .then()
            .catch(error => console.log(error))
    }

    const onSaveBets = () =>
    {
        const updatedValues = [...bets];
        for(const bet of updatedValues)
        {
            if(notChangeBet(bet))
                continue;
            
            if(invalidBet(bet))
            {
                bet.error = true;
                continue;
            }

            const method = bet.originalHomeTeamScore ? 'PUT' : 'POST';
            sendBets(bet,method)   
        }
        setBets(updatedValues);
    }

    const handleChange = (event: ChangeEvent<HTMLInputElement>, index: number, isHomeScore : boolean): void => 
    {
        const updatedValues = [...bets];
        if(isHomeScore)
        {
            const homeTeamScore = event.target.value;
            updatedValues[index].homeTeamScore = homeTeamScore ? parseInt(homeTeamScore) : null;
        } 
        else
        {
            const awayTeamScore = event.target.value;
            updatedValues[index].awayTeamScore = awayTeamScore ? parseInt(awayTeamScore) : null;
        }

        setBets(updatedValues)
    };

    const betStatus = (bet : Bet) =>
    {
        const positionClass = "absolute top-1 left-2 font-bold text-xs sm:text-sm";
        if((bet.homeTeamScore !== bet.originalHomeTeamScore) || (bet.awayTeamScore !== bet.originalAwayTeamScore))
            return <p className={`${positionClass} text-blue-700`}> ALTERADO </p>
        
        if((bet.homeTeamScore === null) || (bet.awayTeamScore === null))
            return <p className={`${positionClass} text-red-500`}> VAZIO </p>
        
        return <p className={`${positionClass} text-brand-200`}> SALVO </p>
    }

    const scoreClass = "w-8 text-center";
    const versusClass = "w-4 text-center";
    const inputClass = (error: boolean, date : string) => `w-8 border text-center ${error ? 'border-red-500' : 'border-black'}  ${isPastDate(date) ? 'bg-gray-200' : ''}`;
    const teamNameClass = "font-bold text-sm sm:text-lg";
    const teamDivClass = "w-full flex gap-2 items-center ";
    const teamImgSize = "30";
    const arrowsClass = "absolute top-0 text-2xl hover:text-brand-400 hover:cursor-pointer"

    return(

        <div className="flex flex-col w-full xl:w-1/2 gap-6 mb-4">

            <div className="flex flex-col border-2 border-black ">

                <div className="relative flex bg-brand-200 px-4 text-white font-title justify-center">
                    
                    {
                        pageNumber>0 &&
                        <FontAwesomeIcon 
                            className={`${arrowsClass} left-2`}
                            icon={faCaretLeft}
                            onClick={() => getMatcheswithBets(pageNumber-1)}
                        />
                    }
                    PARTIDAS
                    {
                        !lastPage &&
                        <FontAwesomeIcon 
                            className={`${arrowsClass} right-2`}
                            icon={faCaretRight}
                            onClick={() => getMatcheswithBets(pageNumber+1)}
                        />
                    }
                    
                </div>

                {
                    bets.map((bet, index) =>
                    (
                        <div key={index} className="relative flex flex-col items-center border-t-2 border-black p-1">

                            {betStatus(bet)}

                            <div> {toBrDate(bet.match.startMoment)} </div>

                            <div className="flex w-full items-center gap-3">

                                <div className={teamDivClass+'flex-row-reverse flex-grow'}>
                                    <img src={bet.match.homeTeam.imgUri} alt="" width={teamImgSize} height={teamImgSize}></img>
                                    <p className={teamNameClass}>{bet.match.homeTeam.name}</p>
                                </div>

                                <div className="flex flex-col">

                                    <div className="flex gap-1">
                                        <p className={scoreClass}>{bet.match.homeTeamScore}</p>
                                        <p className={versusClass}>X</p>
                                        <p className={scoreClass}>{bet.match.awayTeamScore}</p>
                                    </div>

                                    <div className="flex gap-1">
                                        <input
                                            className={inputClass(bet.error, bet.match.startMoment)}
                                            type="number"
                                            min={0}
                                            max={99}
                                            value={bet.homeTeamScore ?? ''}
                                            disabled={isPastDate(bet.match.startMoment)}
                                            onChange={(event) => handleChange(event, index, true)}
                                        />
                                        <p className={versusClass}>X</p>
                                        <input
                                            className={inputClass(bet.error, bet.match.startMoment)}
                                            type="number"
                                            min={0}
                                            max={99}
                                            value={bet.awayTeamScore ?? ''}
                                            disabled={isPastDate(bet.match.startMoment)}
                                            onChange={(event) => handleChange(event, index, false)}
                                        />
                                    </div>

                                </div>

                                <div className={teamDivClass+' flex-grow'}>
                                    
                                    <img src={bet.match.awayTeam.imgUri} alt="" width={teamImgSize} height={teamImgSize}></img>
                                    <p className={teamNameClass}>{bet.match.awayTeam.name}</p>
                                </div>

                            </div>

                        </div>
                    ))
                }

            </div>

            <div className="mx-auto">
                <Button onClick={onSaveBets}>  SALVAR PALPITES </Button>
            </div>
            
        </div>
    )
}

export default Bets;