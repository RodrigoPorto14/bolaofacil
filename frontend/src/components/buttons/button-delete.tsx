import { useState } from "react";
import Button from "./button"
import ConfirmModal from "../modal/ConfirmModal";

type DeleteButtonProps = 
{
    onClick : any;
}

const DeleteButton = ({ onClick } : DeleteButtonProps) =>
{

    const [modalOpen, setModalOpen] = useState(false);

    const open = () => { setModalOpen(true); }
    const close = () => { setModalOpen(false); }

    const accept = () =>
    {
        onClick();
        close();
    }

    return(

        <>
            <ConfirmModal 
                question="Deseja realmente deletar ?"
                isOpen={modalOpen} 
                onRequestClose={close}
                onAccept={accept}
            />
            <Button 
                color="bg-red-500" 
                hoverColor="hover:bg-red-800" 
                type="button" 
                onClick={open}
            > 
                
                DELETAR 
            
            </Button>
        </>
        
    )
}

export default DeleteButton;