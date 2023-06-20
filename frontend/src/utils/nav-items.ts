export const menuItems = 
[
    {title: 'Meus Bolões', redirect: '/sweepstakes'},
    {title: 'Buscar Bolão', redirect: '/search'}
]

export const configItems = (sweepstakeId : string | undefined) => 
{
    
    const basePath = `/sweepstakes/${sweepstakeId}/config`;
    const items = 
    [
        {title: 'Bolão', redirect: basePath+'/sweepstake'},
        {title: 'Regras', redirect: basePath+'/rules'},
        {title: 'Times', redirect: basePath+'/teams'},
        {title: 'Partidas', redirect: basePath+'/matches'},
        {title: 'Participantes', redirect: basePath+'/participants'},
        {title: 'Solicitações', redirect: basePath+'/requests'}
    ]

    return items
}
