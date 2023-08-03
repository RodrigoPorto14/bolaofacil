import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUserTie } from '@fortawesome/free-solid-svg-icons'

const OwnerIcon = ( {ownerName} : {ownerName : string | undefined}) =>
{
    return(

        <>
            <p className="hidden md:block">{`Criado por: ${ownerName}`}</p>
            <FontAwesomeIcon
                className="text-xl md:hidden"
                icon={faUserTie}
                data-tooltip-id="tooltip" 
                data-tooltip-content={`Criado por: ${ownerName}`}
            />
        </>
    )
}

export default OwnerIcon;