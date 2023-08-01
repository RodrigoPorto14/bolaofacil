import Modal from 'react-modal'
import MainButton from '../buttons/button-main';
import Button from '../buttons/button';

type ConfirmModalProps = 
{
    question : string;
    isOpen : any;
    onRequestClose : any;
    onAccept : any;
}

Modal.setAppElement('#root');

const ConfirmModal = ({ question, isOpen, onRequestClose, onAccept } : ConfirmModalProps) => {
  return (
    <Modal
      isOpen={isOpen}
      overlayClassName="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center"
      className="bg-white p-6 rounded-md"
      shouldCloseOnOverlayClick={false}
    >
      <div className="flex flex-col gap-2">

        <h1 className="text-2xl"> {question} </h1>

        <div className="flex gap-2 justify-center">
            <MainButton onClick={onAccept}> SIM </MainButton>
            <Button 
                color="bg-red-500" 
                hoverColor="hover:bg-red-800" 
                type="button" 
                onClick={onRequestClose}
            > 
                NÃO 
            </Button> 
        </div>
        
      </div>
    </Modal>
  );
};

export default ConfirmModal;