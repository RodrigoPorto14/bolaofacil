export interface IParticipant
{
    sweepstakeName?: string;
    customSweepstake?: boolean;
    role? : string;
}

export interface IContext extends IParticipant
{
    isOwner : () => boolean;
    isOwnerOrAdmin : () => boolean;
}