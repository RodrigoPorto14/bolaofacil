import { useEffect, useState } from "react";
import { makePrivateRequest } from "../utils/request";
import { useParams } from "react-router-dom";

type RankingHeader = 
{
    name: string,
    exactScore : number;
	winnerScore : number;
	scoreDifference : number;
	loserScore : number;
	winner : number;
    points : number;
}

const Ranking = () =>
{

    const { sweepstakeId } = useParams();
    const [ranking, setRanking] = useState<RankingHeader[]>([]);

    useEffect(() =>
    {
        makePrivateRequest({ url: `/boloes/${sweepstakeId}/ranking`})
            .then(response =>
            {
                setRanking(response.data)
            })
            .catch(error => console.log(error))
    },[sweepstakeId])

    const border = "border-2 border-black h-4";
    const headerCellClass = `w-12 ${border}`

    return(

        <table className={border} >
            <thead>
                <tr className="bg-brand-200 text-white">
                    <th className={headerCellClass} >POS</th>
                    <th className={`w-56 h-4 ${border}`}>JOGADOR</th>
                    <th className={headerCellClass}>PTS</th>
                    <th className={headerCellClass}>PE</th>
                    <th className={headerCellClass}>PV</th>
                    <th className={headerCellClass}>DP</th>
                    <th className={headerCellClass}>PP</th>
                    <th className={headerCellClass}>VE</th>
                </tr>
            </thead>

            <tbody className="text-center">
                
                {
                    ranking.map((competitor, index) =>
                    (
                        <tr key={index} className={index%2===0 ? "bg-white" : "bg-brand-100"}>
                            
                            <td className={border}> {index+1} </td>
                            <td className={border}> {competitor.name} </td>
                            <td className={border}> {competitor.points} </td>
                            <td className={border}> {competitor.exactScore} </td>
                            <td className={border}> {competitor.winnerScore} </td>
                            <td className={border}> {competitor.scoreDifference} </td>
                            <td className={border}> {competitor.loserScore} </td>
                            <td className={border}> {competitor.winner} </td>

                        </tr>
                    ))
                }

            </tbody>
        </table>
    )
}

export default Ranking