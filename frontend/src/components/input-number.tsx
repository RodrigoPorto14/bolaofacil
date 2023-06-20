type InputProps<T> = 
{
    label: string;
    register: any;
    name: keyof T;
    errors: any;
    defaultValue?: number;
};
  
function InputNumber<T>({ label, register, name, errors, defaultValue }: InputProps<T>)
{
  
  const toInt = (value : string) => isNaN(parseInt(value)) ? null : parseInt(value)

  return (

    <div className={`flex flex-col`}>

      <label className="px-1">{label}</label>

      <input 
        className='rounded-lg h-7 px-2 w-14'
        type="number"
        min={0}
        max={99}
        defaultValue={defaultValue ?? ''}
        {...register(name, { setValueAs: (value: string) => toInt(value)})}
      />
      {errors[name] && <span className="px-1 text-sm text-red-600">{errors[name].message}</span>}
      
    </div>
  );
}

export default InputNumber