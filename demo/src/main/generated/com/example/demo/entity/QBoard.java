package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -164971147L;

    public static final QBoard board = new QBoard("board");

    public final StringPath boardcontext = createString("boardcontext");

    public final NumberPath<Long> boardid = createNumber("boardid", Long.class);

    public final StringPath boardname = createString("boardname");

    public final DatePath<java.sql.Date> boardwrite = createDate("boardwrite", java.sql.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

