import { ReactNode } from "react";
import { Resource } from "./enums";

export type ResourceProps = 
{
    resource: Resource;
}

export type ResourceSample =
{
    id: number;
    name: string;
    ownerName?: string;
    startMoment?: string;
}

export type Sweepstake = 
{
    id : number;
    name : string;
    tournament : string;
    private_ : boolean;
    ownerName : string;
    hasRequest : boolean;
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

type BetMatch = 
{
    id : number;
    type: string;
    startMoment: string;
    homeTeam : Team;
    awayTeam : Team;
    homeTeamScore: number;
    awayTeamScore: number;
}

export type Bet =
{
    id : number;
    match : BetMatch;
    homeTeamScore: number | null;
    awayTeamScore: number | null;
    originalHomeTeamScore : number | null;
    originalAwayTeamScore : number | null;
    error : boolean;
}

export type RankingHeader = 
{
    name: string,
    exactScore : number;
	winnerScore : number;
	scoreDifference : number;
	loserScore : number;
	winner : number;
    points : number;
}

export type FormProps =
{
    onSubmit : any;
    onDelete? : any;
    buttonName : string;
    create? : boolean;
    resource? : any;
    children?: ReactNode;
    backButton?: boolean;
}

