from pydantic import BaseModel
import datetime

class ColorSchema(BaseModel):
    position: int
    color: str
    class Config:
        orm_mode = True



# class TransactionSchema(BaseModel):
#     donor: str
#     reciever: str
#     taskid: int
#     karma:int
#     date:datetime
 