import { ReactNode } from "react";

export type ResourceProps = 
{
    resource: string;
}

export type ResourceSample =
{
    id: number;
    name: string;
    ownerName?: string;
}

export type Sweepstake = 
{
    id : number;
    name : string;
    private_ : boolean;
    ownerName : string;
}

export type Rule = 
{
    id : number;
    name : string;
    exactScore : number;
	winnerScore : number;
	scoreDifference : number;
	loserScore : number;
	winner : number;
}

export type Team =
{
    id : number;
    name : string;
    imgUri : string;
}

export type Match = 
{
    id : number;
    ruleId: number;
    type: string;
    startMoment: string;
    homeTeamId: number;
    awayTeamId: number;
    homeTeamScore: number;
    awayTeamScore: number;
}

export type Resource = Sweepstake | Rule | Team | Match ;

export type FormProps =
{
    onSubmit : any;
    onDelete? : any;
    buttonName : string;
    create? : boolean;
    resource? : Resource;
    children?: ReactNode;
    gap? : string;
}

