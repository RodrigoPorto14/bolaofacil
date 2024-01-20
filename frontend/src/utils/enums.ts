export enum Resource
{
    SWEEPSTAKE = 'boloes',
    RULE = 'regras',
    TEAM = 'times',
    MATCH = 'partidas'
}

export const _isSweepstake = (resource : Resource) => resource === Resource.SWEEPSTAKE;
export const _isRule = (resource : Resource) => resource === Resource.RULE;
export const _isTeam = (resource : Resource) => resource === Resource.TEAM;
export const _isMatch = (resource : Resource) => resource === Resource.MATCH;
