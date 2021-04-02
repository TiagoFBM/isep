using System;
using mdv.Domain.Nodes;
using mdv.Domain.Shared;
using mdv.Domain.Times;
using mdv.Domain.Validators;

namespace mdv.Domain.Trips {
    public class NodePassage : Entity<NodePassageId> {
        public NodeId nodeID { get; private set; }
        public Time passageTime { get; private set; }

        public NodePassage () { }

        public NodePassage (string nodeID, string passageTime) {
            if (StringValidator.isStringEmptyOrNull (nodeID)) {
                throw new BusinessRuleValidationException (nodeID + " invalid: NodePassage Node can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull (passageTime)) {
                throw new BusinessRuleValidationException (passageTime + " invalid: NodePassage Time can't be null or empty.");
            }

            this.Id = new NodePassageId (Guid.NewGuid ());
            this.nodeID = new NodeId (nodeID);
            this.passageTime = new Time (passageTime);
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                NodePassage nodePassage = (NodePassage) obj;
                return (this.nodeID.Equals (nodePassage.nodeID)) &&
                    (this.passageTime.Equals (nodePassage.passageTime));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (Id, nodeID, passageTime);
        }
    }
}