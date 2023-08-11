export interface IParticipant
{
    sweepstakeName?: string;
    tournament?: string;
    role? : string;
}

export interface IContext extends IParticipant
{
    isCustomTournament : () => boolean;
    isOwner : () => boolean;
    isOwnerOrAdmin : () => boolean;
}