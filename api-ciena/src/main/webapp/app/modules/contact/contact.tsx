import React from 'react';
import { useAppSelector } from 'app/config/store';
import './contact.css';
import { AUTHORITIES } from 'app/config/constants';

export default function Contact() {
  const account = useAppSelector(state => state.authentication.account);

  const contacts = {
    NZHQ: '+64 800 002 600',
    NZHQ_OUTSIDE: '+64 9887 3243',
    USA: '+1 8888 313 339',
    AUSY: '+61 1800 319 388',
    SUPPORTMAIL: 'support@bw-digital.com',
    NZB: '+64 21 593 561',
    AUSB: '+61 449 516164',
    INDO: '+62 21 3009 6404',
    INDOFREE: '+62 800 150 3392',
    THASA: '+61 424 775 130',
    THASAMAIL: 'thbal@bw-digital.com',
    GRAHAM: '+61 407 910 034',
    GRAHAMMAIL: 'grabruc@bw-digital.com',
    DAVID: '+64 21 597 488',
    DAVIDMAIL: 'davisl@bw-digital.com',
    FLORENT: '+65 8136 1498',
    FLORENTMAIL: 'flblo@bw-digital.com',
  };

  const copy = async (value: string) => {
    try {
      await navigator.clipboard.writeText(value);
      console.log('Copied!');
    } catch (err) {
      console.error('Copy failed', err);
    }
  };

  return (
    <div>
      {/* CONTACT SECTION */}
      <div className="container py-4 text-center">
        <h1>Contact Details</h1>

        <div className="row mt-4">
          <div className="col-lg-3">
            <img className="header-image" src="/content/images/nz.svg" alt="NZ" />
            <h4>New Zealand</h4>

            <p>
              NZ Toll-Free ({contacts.NZHQ}) <br />
              NZ Backup ({contacts.NZB})
            </p>

            <button className="btn btn-primary" onClick={() => copy(contacts.NZHQ)}>
              Copy
            </button>
          </div>

          <div className="col-lg-3">
            <img className="header-image" src="/content/images/au.svg" alt="AU" />
            <h4>Australia</h4>

            <p>
              AU Toll-Free ({contacts.AUSY}) <br />
              AU Backup ({contacts.AUSB})
            </p>

            <button className="btn btn-primary" onClick={() => copy(contacts.AUSY)}>
              Copy
            </button>
          </div>

          <div className="col-lg-3">
            <img className="header-image" src="/content/images/us.svg" alt="US" />
            <h4>USA</h4>

            <p>USA Toll-Free ({contacts.USA})</p>

            <button className="btn btn-primary" onClick={() => copy(contacts.USA)}>
              Copy
            </button>
          </div>

          <div className="col-lg-3">
            <img className="header-image" src="/content/images/id.svg" alt="ID" />
            <h4>Indonesia</h4>

            <p>
              Indo Toll-Free ({contacts.INDOFREE}) <br />
              Indo Local ({contacts.INDO})
            </p>

            <button className="btn btn-primary" onClick={() => copy(contacts.INDOFREE)}>
              Copy
            </button>
          </div>
        </div>
      </div>

      {/* ESCALATION SECTION */}

      {account?.authorities?.includes('ROLE_USER') && (
        <div className="container py-4">
          <h1 className="text-center">Escalation Details</h1>

          <div className="table-responsive mt-4">
            <table className="table table-striped text-center">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Role</th>
                  <th>Phone</th>
                  <th>Email</th>
                </tr>
              </thead>

              <tbody>
                <tr>
                  <td>Worldwide Access</td>
                  <td>Hawaiki NOC</td>
                  <td>{contacts.NZHQ_OUTSIDE}</td>
                  <td>{contacts.SUPPORTMAIL}</td>
                </tr>

                <tr>
                  <td>Thasa Balasubramaniam</td>
                  <td>Head of Provisioning</td>
                  <td>{contacts.THASA}</td>
                  <td>{contacts.THASAMAIL}</td>
                </tr>

                <tr>
                  <td>Graham Bruce</td>
                  <td>Director Operations</td>
                  <td>{contacts.GRAHAM}</td>
                  <td>{contacts.GRAHAMMAIL}</td>
                </tr>

                <tr>
                  <td>David Slessor</td>
                  <td>Project Director</td>
                  <td>{contacts.DAVID}</td>
                  <td>{contacts.DAVIDMAIL}</td>
                </tr>

                <tr>
                  <td>Florent Blot</td>
                  <td>CBO Datacenters</td>
                  <td>{contacts.FLORENT}</td>
                  <td>{contacts.FLORENTMAIL}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}
