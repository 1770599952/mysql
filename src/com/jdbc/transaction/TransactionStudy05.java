package com.jdbc.transaction;

/**
 * 	事务部考虑隔离性引发的问题：多个线程开启各自事务操作数据库中数据时，数据库系统要负责隔离操作，以保证各个线程在获取数据时的准确性.
 * 	脏读：一个事务内读取了另一个事务未提交的数据。
 * 	不可重复读 ：一个事务内读取了另一个事务提交的数据。（本质并无重复，但是在实际中，如果一个事务内读取表中某一行数据，多次读取结果结果不能是不对的。）
 * 	虚读（幻读）：在一个事务内读取到了别的事务插入的数据，导致前后读取不一样。
 * 	http://blog.csdn.net/yerenyuan_pku/article/details/52215281 Mysql数据库事务讲解
 * */
public class TransactionStudy05 {

	public static void main(String[] args) {

	}

}
