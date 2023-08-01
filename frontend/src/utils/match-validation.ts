import { Bet } from "./type";
import { isPastDate } from "./date-handler";

export const scorableMatch = (homeScore : number|null|undefined, awayScore : number|null|undefined, startMoment : string) =>
{
    console.log(homeScore, awayScore, startMoment);
    console.log( new Date(startMoment).getTime() , new Date().getTime())
    return (homeScore == null && awayScore == null ) || isPastDate(startMoment)
}

const validBestOf = (bestOf : number, scoreA : number, scoreB : number) => 
{
    const maxScore = Math.ceil(bestOf/2);
    return (scoreA + scoreB <= bestOf) && (scoreA === maxScore || scoreB === maxScore)
}
    
export const matchValidation = (homeTeamScore : number, awayTeamScore : number, matchType : string) => 
{
    if(nullScore(homeTeamScore, awayTeamScore) || undefinedScore(homeTeamScore, awayTeamScore))
        return true;

    if((homeTeamScore === null) !== (awayTeamScore === null))
        return false

    for(let num = 1; num<=5; num+=2)
    {
        if(matchType === `MD${num}` && !validBestOf(num, homeTeamScore, awayTeamScore))
            return false
    }

    return true
}

const undefinedScore = (homeScore : number|undefined, awayScore : number|undefined) =>
    homeScore === undefined && awayScore === undefined;

const nullScore = (homeScore : number|null, awayScore : number|null) => 
    homeScore === null && awayScore === null;

const invalidScore = (bet : Bet) =>
bet.homeTeamScore as number < 0 || bet.homeTeamScore as number > 99 || 
bet.awayTeamScore as number < 0 || bet.awayTeamScore as number > 99

export const notChangeBet = (bet : Bet) => 
    bet.homeTeamScore === bet.originalHomeTeamScore && bet.awayTeamScore === bet.originalAwayTeamScore

export const invalidBet = (bet : Bet) =>
    nullScore(bet.homeTeamScore, bet.awayTeamScore) ||
    invalidScore(bet) ||
    !matchValidation(bet.homeTeamScore as number, bet.awayTeamScore as number, bet.match.type)



    