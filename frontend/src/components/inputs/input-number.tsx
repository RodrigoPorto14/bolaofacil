import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCircleQuestion } from '@fortawesome/free-solid-svg-icons'
import ErrorMessage from './error-message';

type InputProps<T> = 
{
    label: string;
    register: any;
    name: keyof T;
    errors: any;
    defaultValue?: number;
    tooltip?: string;
};
  
function InputNumber<T>({ label, register, name, errors, defaultValue, tooltip }: InputProps<T>)
{
  
  const toInt = (value : string) => isNaN(parseInt(value)) ? null : parseInt(value)

  return (

    <div className={`flex flex-col`}>

      <div className="flex gap-1 items-center">
        <label className="px-1">{label}</label>
        {
            tooltip &&
            <FontAwesomeIcon
            icon={faCircleQuestion}
            className="text-gray-600 hover:text-black cursor-pointer"
            data-tooltip-id="tooltip" 
            data-tooltip-content={tooltip}
          />
        }
        
      </div>
      

      <input 
        className='text-center rounded-lg h-7 px-2 w-14'
        type="number"
        min={0}
        max={99}
        defaultValue={defaultValue ?? ''}
        {...register(name, { setValueAs: (value: string) => toInt(value)})}
      />
      { errors[name] && <ErrorMessage> {errors[name].message} </ErrorMessage> }
      
    </div>
  );
}

export default InputNumber