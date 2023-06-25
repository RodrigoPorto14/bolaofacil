import { Bet } from "./type";

const validBestOf = (bestOf : number, scoreA : number, scoreB : number) => 
{
    const maxScore = Math.ceil(bestOf/2);
    return (scoreA + scoreB <= bestOf) && (scoreA === maxScore || scoreB === maxScore)
}
    
export const matchValidation = (homeTeamScore : number, awayTeamScore : number, matchType : string) => 
{
    if((homeTeamScore === null) !== (awayTeamScore === null))
        return false

    for(let num = 1; num<=5; num+=2)
    {
        if(matchType === `MD${num}` && !validBestOf(num, homeTeamScore, awayTeamScore))
            return false
    }

    return true
}

export const nullScore = (bet : Bet) => 
    bet.homeTeamScore === null && bet.awayTeamScore === null;

export const equalScore = (bet : Bet) => 
    bet.homeTeamScore === bet.originalHomeTeamScore && bet.awayTeamScore === bet.originalAwayTeamScore

export const invalidScore = (bet : Bet) =>
    bet.homeTeamScore as number < 0 || bet.homeTeamScore as number > 99 || 
    bet.awayTeamScore as number < 0 || bet.awayTeamScore as number > 99

    