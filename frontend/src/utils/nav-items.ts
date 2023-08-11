import { useParticipant } from "../context/ParticipantProvider/useParticipant";

export const menuItems = 
[
    {title: 'Meus Bolões', redirect: '/sweepstakes'},
    {title: 'Buscar Bolão', redirect: '/search'},
    {title: 'Meu Perfil', redirect: '/user'}
]

export const ConfigItems = (sweepstakeId? : string) => 
{
    const basePath = `/sweepstakes/${sweepstakeId}`;
    const participant = useParticipant();
    const isOwner = participant.isOwner();
    const isOwnerOrAdmin = participant.isOwnerOrAdmin();
    const isCustom = participant.isCustomTournament();
    const items = []

    if(isOwner)
        items.push({title: 'Bolão', redirect: basePath+'/info'})

    if(isCustom && isOwnerOrAdmin)
    {
        items.push({title: 'Regras', redirect: basePath+'/rules'});
        items.push({title: 'Times', redirect: basePath+'/teams'});
        items.push({title: 'Partidas', redirect: basePath+'/matches'});
    }
    
    if(isOwner)
        items.push({title: 'Participantes', redirect: basePath+'/participants'})

    if(isOwnerOrAdmin)
        items.push({title: 'Solicitações', redirect: basePath+'/requests'});

    return items
}
