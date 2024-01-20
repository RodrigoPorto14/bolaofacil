import { RankingHeader } from "../../utils/types";

type RankingProps = 
{
    ranking : RankingHeader[];
}

const Ranking = ({ ranking } : RankingProps) =>
{

    const border = "border-2 border-black";
    const headerCellClass = `w-12 ${border}`;

    return(
        <div className="max-h-[500px] overflow-y-auto">
            <table className={border}>
            <thead>
                <tr className="bg-brand-200 text-white">
                    <th className={headerCellClass} data-tooltip-id="tooltip" data-tooltip-content="Posição" >POS</th>
                    <th className={`w-56 ${border}`} data-tooltip-id="tooltip" data-tooltip-content="Jogador" >JOGADOR</th>
                    <th className={headerCellClass} data-tooltip-id="tooltip" data-tooltip-content="Pontos">PTS</th>
                    <th className={headerCellClass} data-tooltip-id="tooltip" data-tooltip-content="Placar Exato">PE</th>
                    <th className={headerCellClass} data-tooltip-id="tooltip" data-tooltip-content="Placar do Vencedor">PV</th>
                    <th className={headerCellClass} data-tooltip-id="tooltip" data-tooltip-content="Diferença do Placar">DP</th>
                    <th className={headerCellClass} data-tooltip-id="tooltip" data-tooltip-content="Placar do Perdedor">PP</th>
                    <th className={headerCellClass} data-tooltip-id="tooltip" data-tooltip-content="Vencedor">VE</th>
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
        </div>
        
    )
}

export default Ranking