import { format } from 'date-fns';

export const toBrDate = (date : string) => date ? format(new Date(date), 'dd/MM/yyyy HH:mm') : ''

export const isPastDate = (date : string) => new Date(date) < new Date();
