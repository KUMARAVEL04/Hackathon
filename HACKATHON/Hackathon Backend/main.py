from fastapi import FastAPI, Depends, HTTPException
from models import Base, colors 
from schemas import ColorSchema
from database import engine,SessionLocal
from sqlalchemy.orm import Session
import uvicorn



Base.metadata.create_all(bind=engine)

app = FastAPI()




def get_db():
    try:
        db = SessionLocal()
        yield db
    finally:
        db.close()

@app.get("/")
async def home(db: Session = Depends(get_db)):
    return db.query(colors).all()

@app.get("/clear/{size}")
async def clear(size:int,db: Session = Depends(get_db)):
    poss = db.query(colors).all()
    if(poss):
        for p in poss:
            db.delete(p)
        db.commit()
    for i in range(0,size,1):
        db.add(colors(position=i,color="#FFFFFF"))
        db.commit()
    poss2 = db.query(colors).all()
    return poss2

@app.get("/return")
async def ret(db: Session = Depends(get_db)):
    poss2 = db.query(colors).all()
    return poss2


@app.post("/ab")
async def ab(request:ColorSchema,db: Session = Depends(get_db)):
    user = db.query(colors).filter(colors.position==request.position).first()
    if(user):
        user.color=request.color
        db.commit()
        db.refresh(user)
        return db.query(colors).all()
    else:
        colox =colors(position=request.position,color=request.color)
        db.add(colox)
        db.commit()
        return db.query(colors).all()


# if __name__ == '__main__':
#     uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)


    # uvicorn main:app --host 0.0.0.0 --port 8000