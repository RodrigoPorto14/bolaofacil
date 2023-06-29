export const menuItems = 
[
    {title: 'Meus Bolões', redirect: '/sweepstakes'},
    {title: 'Buscar Bolão', redirect: '/search'}
]

export const configItems = (sweepstakeId? : string, role? : string, tournament? : string) => 
{
    
    const basePath = `/sweepstakes/${sweepstakeId}/config`;
    const isOwner = role === "OWNER";
    const isOwnerOrAdmin = isOwner || role === "ADMIN";
    const isCustom = tournament === "CUSTOM"
    const items = []

    if(isOwner)
        items.push({title: 'Bolão', redirect: basePath+'/sweepstake'})

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
