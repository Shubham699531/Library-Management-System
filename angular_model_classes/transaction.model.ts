import { Book } from './book.model';
import { Student } from './student.model';

export class Transaction{
    transactionId:number;
    dateOfIssue:Date;
    dateOfReturn:Date;
    amount:number;
    review:string;
    transactionStatus:string;
    book:Book;
    student:Student;
}